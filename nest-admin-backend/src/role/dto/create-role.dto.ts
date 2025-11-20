import { ApiProperty } from '@nestjs/swagger'

export class CreateRoleDto {
  @ApiProperty({ example: 'Admin', description: 'The name of the role' })
  roleName: string

  @ApiProperty({ example: 'admin', description: 'The value of the role' })
  roleValue: string

  @ApiProperty({ example: 1, description: 'The status of the role (1: active, 0: disabled)' })
  status?: number

  @ApiProperty({ example: 'Administrator role', description: 'The remark of the role' })
  remark?: string

  @ApiProperty({
    example: ['menu-id-1', 'menu-id-2'],
    description: 'Array of menu IDs to associate with the role',
    required: false,
    type: [String]
  })
  menuIds?: string[]
}
