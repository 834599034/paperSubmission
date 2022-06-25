import { Message } from 'element-ui'
let messageInstance = null
export const message = options => {
  if (messageInstance) {
    messageInstance.close()
  }
  messageInstance = Message(options)
}
['error', 'success', 'info', 'warning'].forEach(type => {
  message[type] = options => {
    if (typeof options === 'string') {
      options = {
        message: options
      }
    }
    options.type = type
    return message(options)
  }
})
