import request from '@/axios'
import type { DepartmentItem } from './types'
import { DepartmentListResponse, DepartmentUserParams, DepartmentUserResponse } from './types'

export interface DepartmentParams {
  name?: string
  status?: number
  pageIndex?: number
  pageSize?: number
}

export type DepartmentType = DepartmentItem

export const getDepartmentApi = (params?: DepartmentParams) => {
  return request.get<DepartmentListResponse>({ url: '/api/department', params })
}

export const getUserByIdApi = (params: DepartmentUserParams) => {
  const query = {
    page: params.pageIndex,
    pageSize: params.pageSize,
    deptId: params.id,
    username: params.account,
    nickname: params.username
  }
  return request.get<DepartmentUserResponse>({ url: '/api/user', params: query })
}

export const deleteUserByIdApi = (ids: string[] | number[]) => {
  return request.post({ url: '/api/user/delete', data: { ids } })
}

export const saveUserApi = (data: any) => {
  if (data.id) {
    return request.patch({ url: `/api/user/${data.id}`, data })
  } else {
    return request.post({ url: '/api/user', data })
  }
}

export const saveDepartmentApi = (data: any) => {
  if (data.id) {
    return request.patch({ url: `/api/department/${data.id}`, data })
  } else {
    return request.post({ url: '/api/department', data })
  }
}

export const deleteDepartmentApi = (ids: string[] | number[]) => {
  return request.post({ url: '/api/department/delete', data: { ids } })
}

export const getDepartmentListApi = (
  params: DepartmentParams
): Promise<IResponse<DepartmentListResponse>> => {
  return request.get({ url: '/api/department', params })
}
