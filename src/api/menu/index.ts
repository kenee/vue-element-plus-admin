import request from '@/axios'

export interface MenuParams {
  title?: string
}

export const getMenuListApi = (
  params?: MenuParams
): Promise<IResponse<{ list: AppCustomRouteRecordRaw[] }>> => {
  return request.get({ url: '/api/menu', params })
}
export const saveMenuApi = (data: any) => {
  if (data.id) {
    return request.patch({ url: `/api/menu/${data.id}`, data })
  } else {
    return request.post({ url: '/api/menu', data })
  }
}
export const deleteMenuApi = (id: string) => {
  return request.delete({ url: `/api/menu/${id}` })
}
