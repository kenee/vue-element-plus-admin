import request from '@/axios'

// 获取所有字典
export const getDictApi = () => {
  return request.get({ url: '/api/dictionary/list' })
}

// 模拟获取某个字典
export const getDictOneApi = async () => {
  return request.get({ url: '/api/dictionary/one' })
}
