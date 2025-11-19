import { ApiProperty } from '@nestjs/swagger';

export class CreateMenuDto {
    @ApiProperty({ example: '0', description: 'The parent ID of the menu' })
    parentId?: string;

    @ApiProperty({ example: '/dashboard', description: 'The path of the menu' })
    path?: string;

    @ApiProperty({ example: 'Layout', description: 'The component of the menu' })
    component?: string;

    @ApiProperty({ example: 'Dashboard', description: 'The title of the menu' })
    title: string;

    @ApiProperty({ example: 'dashboard', description: 'The icon of the menu' })
    icon?: string;

    @ApiProperty({ example: 1, description: 'The type of the menu (0: Directory, 1: Menu, 2: Button)' })
    type?: number;

    @ApiProperty({ example: 'dashboard:list', description: 'The permission identifier' })
    permission?: string;

    @ApiProperty({ example: 1, description: 'The sort order' })
    sort?: number;
}
