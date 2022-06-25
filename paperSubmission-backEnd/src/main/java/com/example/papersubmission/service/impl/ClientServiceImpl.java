package com.example.papersubmission.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.papersubmission.entity.Client;
import com.example.papersubmission.entity.PaperType;
import com.example.papersubmission.entity.ReviewerMajor;
import com.example.papersubmission.exceptions.EmailException;
import com.example.papersubmission.exceptions.PhoneException;
import com.example.papersubmission.exceptions.UsernameException;
import com.example.papersubmission.service.ClientService;
import com.example.papersubmission.mapper.ClientMapper;
import com.example.papersubmission.service.PaperTypeService;
import com.example.papersubmission.service.ReviewerMajorService;
import com.example.papersubmission.utils.Query;
import com.example.papersubmission.vo.ClientVo;
import com.example.papersubmission.vo.LoginVo;
import com.example.papersubmission.vo.RegisterVo;
import com.example.papersubmission.vo.UpdatePasswordVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
* @author h8345
* @description 针对表【client】的数据库操作Service实现
* @createDate 2022-05-21 20:55:59
*/
@Service
public class ClientServiceImpl extends ServiceImpl<ClientMapper, Client>
    implements ClientService{

    @Autowired
    ReviewerMajorService reviewerMajorService;

    @Autowired
    PaperTypeService paperTypeService;


    @Override
    public IPage<Client> queryPage(Map<String,Object> params){
        IPage<Client> page = this.page(new Query<Client>().getPage(params), new QueryWrapper<Client>());
        return page;
    }

    @Override
    public Integer checkLogin(LoginVo loginVo) {
//        获取当前用户
        Client user_name = this.getOne(new QueryWrapper<Client>().eq("user_name", loginVo.getUserName()));
        if(user_name==null){
            return -1;
        }
        //将用户输入密码与盐值组合进行MD5加密
        String password = loginVo.getPassword();
        StringBuilder append = new StringBuilder().append(password).append(user_name.getSalt());
        String md5Hex1 = DigestUtil.md5Hex(append.toString());
        //
        if(user_name.getPassword().equals(md5Hex1)){
            return user_name.getId();
        }else {
            return -1;
        }

    }

    @Override
    public void register(RegisterVo registerVo) {
        //设置其它的默认信息
        //检查用户名和邮箱是否唯一。感知异常，异常机制
        checkUserNameUnique(registerVo.getUserName());
        checkEmailUnique(registerVo.getEmail());

        Client client=new Client();
        BeanUtils.copyProperties(registerVo,client);
        client.setSalt(createSalt());
        client.setPassword(DigestUtil.md5Hex(registerVo.getPassword()+client.getSalt()));
        this.save(client);
        System.out.println(client.getId());
    }



    public void checkEmailUnique(String email) throws EmailException {

        Long emailCount = this.baseMapper.selectCount(new QueryWrapper<Client>().eq("email", email));

        if (emailCount > 0) {
            throw new EmailException();
        }

    }

    /**
    * 修改密码
    */

    @Override
    public Boolean updatePassword(UpdatePasswordVo updatePasswordVo,Integer userId) {
        LoginVo loginVo=new LoginVo();
        String userName = this.getById(userId).getUserName();
        loginVo.setUserName(userName);
        loginVo.setPassword(updatePasswordVo.getOldPassword());
//        校验原密码
        if(this.checkLogin(loginVo)!=-1){
//          原密码校验成功，生成新盐值并存储新密码
            Client client=new Client();
            client.setId(userId);
            BeanUtils.copyProperties(loginVo,client);
            client.setSalt(createSalt());
            client.setPassword(DigestUtil.md5Hex(updatePasswordVo.getNewPassword()+client.getSalt()));
            return this.updateById(client);
        }else{
//            原密码校验失败
           return false;

        }

    }

    @Override
    public List<Client> searchAuthor(String key) {
        if(key!=null){
            List<Client> list = this.list(new QueryWrapper<Client>().ne("role",2).and(clientQueryWrapper -> clientQueryWrapper.like("user_name", key).or().like("email", key).or().like("real_name", key)));
            return list;
        }
        return null;
    }

    @Override
    public ClientVo getClientInfo(Integer clientId) {
        Client client = this.getById(clientId);
        List<ReviewerMajor> reviewer_id = reviewerMajorService.list(new QueryWrapper<ReviewerMajor>().eq("reviewer_id", clientId));
        ClientVo clientVo = new ClientVo();
        BeanUtils.copyProperties(client,clientVo);
        List<String> collect = reviewer_id.stream().map(reviewerMajor -> {
            return reviewerMajor.getMajor();
        }).collect(Collectors.toList());

        List<Integer> collect1 = reviewer_id.stream().map(reviewerMajor -> reviewerMajor.getMajorId()).collect(Collectors.toList());
        clientVo.setMajor(collect);
        clientVo.setMajorIds(collect1);

        return clientVo;
    }

    @Override
    @Transactional
    public void updateClientInfo(ClientVo clientVo, Integer userId) {
        Client client = new Client();
        BeanUtils.copyProperties(clientVo,client);
        client.setId(userId);
        //删除所有老的major信息
        this.reviewerMajorService.remove(new QueryWrapper<ReviewerMajor>().eq("reviewer_id",userId));
        List<Integer> majorIds = clientVo.getMajorIds();
        if(majorIds!=null){
            List<ReviewerMajor> majors = majorIds.stream().map(major -> {
                ReviewerMajor reviewerMajor = new ReviewerMajor();
                PaperType byId = paperTypeService.getById(major);
                reviewerMajor.setReviewerId(userId);
                reviewerMajor.setMajorId(major);
                reviewerMajor.setMajor(byId.getTypeName());
                return reviewerMajor;
            }).collect(Collectors.toList());
            this.reviewerMajorService.saveBatch(majors);
            this.updateById(client);
        }

        this.updateById(client);



    }

    /**
     * 获取审稿人列表
     */
    @Override
    public List<ClientVo> getAllReviewers() {
        List<Client> clients = this.list(new QueryWrapper<Client>().eq("role", 1));
        List<ClientVo> reviewers = clients.stream().map(client -> {
            ClientVo clientVo = new ClientVo();
            BeanUtils.copyProperties(client, clientVo);
            List<ReviewerMajor> reviewer_id = reviewerMajorService.list(new QueryWrapper<ReviewerMajor>().eq("reviewer_id", client.getId()));
            List<String> collect = reviewer_id.stream().map(reviewerMajor -> {
                return reviewerMajor.getMajor();
            }).collect(Collectors.toList());
            List<Integer> collect1 = reviewer_id.stream().map(reviewerMajor -> reviewerMajor.getMajorId()).collect(Collectors.toList());
            clientVo.setMajor(collect);
            clientVo.setMajorIds(collect1);
            return clientVo;
        }).collect(Collectors.toList());
        return reviewers;
    }


    public void checkPhoneUnique(String phone) throws PhoneException {

        Long phoneCount = this.baseMapper.selectCount(new QueryWrapper<Client>().eq("telphone", phone));

        if (phoneCount > 0) {
            throw new PhoneException();
        }

    }


    public void checkUserNameUnique(String userName) throws UsernameException {

        Long usernameCount = this.baseMapper.selectCount(new QueryWrapper<Client>().eq("user_name",userName));

        if (usernameCount > 0) {
            throw new UsernameException();
        }
    }
    //    生成随机盐值
    private String createSalt(){
        char[] chars = ("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz" +
                "1234567890!@#$%^&*()_+").toCharArray();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 16; i++){
            //Random().nextInt()返回值为[0,n)
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();

    }

}




