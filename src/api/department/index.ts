import request from '@/axios'
import type { DepartmentItem } from './types'
import { DepartmentListResponse, DepartmentUserParams, DepartmentUserResponse } from './types'

export interface DepartmentParams {
  name?: string
  status?: number
}

export type DepartmentType = DepartmentItem

export const getDepartmentApi = () => {
  return request.get<DepartmentListResponse>({ url: '/mock/department/list' })
}

export const getUserByIdApi = (params: DepartmentUserParams) => {
  return request.get<DepartmentUserResponse>({ url: '/mock/department/users', params })
}

export const deleteUserByIdApi = (ids: string[] | number[]) => {
  return request.post({ url: '/mock/department/user/delete', data: { ids } })
}

export const saveUserApi = (data: any) => {
  return request.post({ url: '/mock/department/user/save', data })
}

export const saveDepartmentApi = (data: any) => {
  return request.post({ url: '/mock/department/save', data })
}

export const deleteDepartmentApi = (ids: string[] | number[]) => {
  return request.post({ url: '/mock/department/delete', data: { ids } })
}

export const getDepartmentListApi = (params: DepartmentParams): Promise<IResponse<DepartmentType[]>> => {
  return request.get({ url: '/api/department', params })
}
