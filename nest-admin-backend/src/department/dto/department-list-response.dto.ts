import { ApiProperty } from '@nestjs/swagger'

export class DepartmentItemDto {
  @ApiProperty({ description: 'Department ID' })
  id: string

  @ApiProperty({ description: 'Department name' })
  departmentName: string

  @ApiProperty({ description: 'Child departments', type: [DepartmentItemDto], required: false })
  children?: DepartmentItemDto[]
}

export class DepartmentListResponseDto {
  @ApiProperty({ description: 'Department list', type: [DepartmentItemDto] })
  list: DepartmentItemDto[]

  @ApiProperty({ description: 'Total count' })
  total: number
}
