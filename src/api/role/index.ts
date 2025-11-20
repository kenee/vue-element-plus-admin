import request from '@/axios'

export interface RoleParams {
  roleName?: string
}

export interface RoleType {
  id: string
  roleName: string
  roleValue: string
  status: number
  remark: string
  createTime: string
  menu?: any[]
}

export const getRoleListApi = (
  params?: RoleParams
): Promise<IResponse<{ list: RoleType[]; total: number }>> => {
  return request.get({ url: '/api/role', params })
}

export const saveRoleApi = (data: Partial<RoleType>): Promise<IResponse> => {
  return request.post({ url: '/api/role', data })
}

export const updateRoleApi = (id: string, data: Partial<RoleType>): Promise<IResponse> => {
  return request.patch({ url: `/api/role/${id}`, data })
}

export const deleteRoleApi = (id: string): Promise<IResponse> => {
  return request.delete({ url: `/api/role/${id}` })
}
