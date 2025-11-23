import request from '@/axios'

export interface MenuParams {
  title?: string
}

export const getMenuListApi = (
  params?: MenuParams
): Promise<IResponse<{ list: AppCustomRouteRecordRaw[] }>> => {
  return request.get({ url: '/api/menu', params })
}
