import { ApiProperty } from '@nestjs/swagger'

export class RouteMetaDto {
  @ApiProperty({ description: 'Route title', required: false })
  title?: string

  @ApiProperty({ description: 'Route icon', required: false })
  icon?: string

  @ApiProperty({ description: 'Always show', required: false })
  alwaysShow?: boolean

  @ApiProperty({ description: 'No cache', required: false })
  noCache?: boolean

  @ApiProperty({ description: 'Hidden', required: false })
  hidden?: boolean

  @ApiProperty({ description: 'Breadcrumb', required: false })
  breadcrumb?: boolean

  @ApiProperty({ description: 'Affix', required: false })
  affix?: boolean

  @ApiProperty({ description: 'Active menu', required: false })
  activeMenu?: string

  @ApiProperty({ description: 'No tags view', required: false })
  noTagsView?: boolean

  @ApiProperty({ description: 'Can to', required: false })
  canTo?: boolean

  @ApiProperty({ description: 'Permissions', type: [String], required: false })
  permission?: string[];

  [key: string]: any
}

export class RouteItemDto {
  @ApiProperty({ description: 'Route name' })
  name: string

  @ApiProperty({ description: 'Route path' })
  path: string

  @ApiProperty({ description: 'Component path' })
  component: string

  @ApiProperty({ description: 'Redirect path' })
  redirect: string

  @ApiProperty({ description: 'Route meta', type: RouteMetaDto })
  meta: RouteMetaDto

  @ApiProperty({ description: 'Child routes', type: [RouteItemDto], required: false })
  children?: RouteItemDto[]
}
