import request from '@/axios'
import type { TableData } from './types'

export const getTableListApi = (params: any) => {
  return request.get({ url: '/api/table/example/list', params })
}

export const getCardTableListApi = (params: any) => {
  return request.get({ url: '/api/table/card/list', params })
}

export const getTreeTableListApi = (params: any) => {
  return request.get({ url: '/api/table/example/treeList', params })
}

export const saveTableApi = (data: Partial<TableData>): Promise<IResponse> => {
  return request.post({ url: '/api/table/example/save', data })
}

export const getTableDetApi = (id: string): Promise<IResponse<TableData>> => {
  return request.get({ url: '/api/table/example/detail', params: { id } })
}

export const delTableListApi = (ids: string[] | number[]): Promise<IResponse> => {
  return request.post({ url: '/api/table/example/delete', data: { ids } })
}
