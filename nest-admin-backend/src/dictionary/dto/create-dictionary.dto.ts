import { ApiProperty } from '@nestjs/swagger';

export class CreateDictionaryDto {
    @ApiProperty({ example: 'Gender', description: 'The name of the dictionary' })
    dictName: string;

    @ApiProperty({ example: 'gender', description: 'The code of the dictionary' })
    dictCode: string;

    @ApiProperty({ example: 1, description: 'The status of the dictionary (1: active, 0: disabled)' })
    status?: number;

    @ApiProperty({ example: 'Gender dictionary', description: 'The remark of the dictionary' })
    remark?: string;
}
