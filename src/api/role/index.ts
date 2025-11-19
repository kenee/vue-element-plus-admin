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
}

export const getRoleListApi = (params: RoleParams): Promise<IResponse<RoleType[]>> => {
  return request.get({ url: '/api/role', params })
}
