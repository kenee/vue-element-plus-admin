import { ApiProperty } from '@nestjs/swagger'

export class CreateDepartmentDto {
  @ApiProperty({ example: '0', description: 'The parent ID of the department' })
  parentId?: string

  @ApiProperty({ example: 'Development', description: 'The name of the department' })
  name?: string

  @ApiProperty({
    example: 'Development',
    description: 'The name of the department (alias for name)'
  })
  departmentName?: string

  @ApiProperty({ example: 1, description: 'The sort order' })
  sort?: number

  @ApiProperty({ example: 1, description: 'The status of the department (1: active, 0: disabled)' })
  status?: number

  @ApiProperty({ example: 'Development Department', description: 'The remark of the department' })
  remark?: string
}
