const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  userInfo: s => s.user.userInfo,
  ossPolicy: s => s.service.fileUploadPolicy,
  paperStatus: s => s.paper.paperStatus,
  reviewerStatus: s => s.reviewer.reviewerStatus,
  userRoles: s => s.user.roles,
  userRole: s => s.user.userRole,
  addRouters: s => s.permission.addRouters,
  routers: s => s.permission.routers
}
export default getters
