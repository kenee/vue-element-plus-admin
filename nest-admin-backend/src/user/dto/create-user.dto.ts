import { ApiProperty } from '@nestjs/swagger'

export class CreateUserDto {
  @ApiProperty({ example: 'admin', description: 'The username of the User' })
  username: string

  @ApiProperty({ example: '123456', description: 'The password of the User' })
  password?: string

  @ApiProperty({ example: 'Admin User', description: 'The nickname of the User' })
  nickname?: string

  @ApiProperty({ example: 'admin@example.com', description: 'The email of the User' })
  email?: string

  @ApiProperty({ example: '1', description: 'The department ID' })
  deptId?: string

  @ApiProperty({ description: 'The department object (from frontend)' })
  department?: { id: string }

  @ApiProperty({ description: 'The role IDs (from frontend)' })
  role?: string[]

  @ApiProperty({ example: 1, description: 'The status of the User (1: active, 0: disabled)' })
  status?: number
}
