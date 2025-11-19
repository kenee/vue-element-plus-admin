import { ApiProperty } from '@nestjs/swagger';

export class LoginDto {
    @ApiProperty({ example: 'admin', description: 'The username' })
    username: string;

    @ApiProperty({ example: '123456', description: 'The password' })
    password: string;
}
