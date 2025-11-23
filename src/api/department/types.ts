export interface DepartmentItem {
  id: string
  departmentName: string
  children?: DepartmentItem[]
  status: number
  sort: number
  remark: string
  createTime: string
}

export interface DepartmentListResponse {
  list: DepartmentItem[]
  total: number
}

export interface DepartmentUserParams {
  pageSize: number
  pageIndex: number
  id: string
  username?: string
  account?: string
}

export interface DepartmentUserItem {
  id: string
  username: string
  account: string
  email: string
  createTime: string
  role: string[]
  roles?: any[]
  department: DepartmentItem
}

export interface DepartmentUserResponse {
  list: DepartmentUserItem[]
  total: number
}
