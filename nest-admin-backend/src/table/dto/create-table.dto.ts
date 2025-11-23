import { ApiProperty } from '@nestjs/swagger'
import { IsString, IsInt, IsOptional, IsDateString } from 'class-validator'

export class CreateTableDto {
  @ApiProperty({ description: 'Author' })
  @IsString()
  author: string

  @ApiProperty({ description: 'Title' })
  @IsString()
  title: string

  @ApiProperty({ description: 'Content' })
  @IsOptional()
  @IsString()
  content?: string

  @ApiProperty({ description: 'Importance' })
  @IsInt()
  importance: number

  @ApiProperty({ description: 'Display Time' })
  @IsDateString()
  displayTime: Date

  @ApiProperty({ description: 'Pageviews' })
  @IsInt()
  pageviews: number

  @ApiProperty({ description: 'Image URI' })
  @IsOptional()
  @IsString()
  imageUri?: string

  @ApiProperty({ description: 'Parent ID for tree structure' })
  @IsOptional()
  @IsString()
  parentId?: string
}
