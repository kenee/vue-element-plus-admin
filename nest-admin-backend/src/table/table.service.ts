import { Injectable } from '@nestjs/common'
import { InjectRepository } from '@nestjs/typeorm'
import { Repository, Like, In } from 'typeorm'
import { TableExample } from './entities/table-example.entity'
import { CardExample } from './entities/card-example.entity'
import { CreateTableDto } from './dto/create-table.dto'
import { UpdateTableDto } from './dto/update-table.dto'
import { QueryTableDto, QueryCardDto } from './dto/query-table.dto'

@Injectable()
export class TableService {
  constructor(
    @InjectRepository(TableExample)
    private tableRepository: Repository<TableExample>,
    @InjectRepository(CardExample)
    private cardRepository: Repository<CardExample>
  ) {}

  // Table Example Methods
  async create(createTableDto: CreateTableDto) {
    const table = this.tableRepository.create(createTableDto)
    if (createTableDto.parentId) {
      const parent = await this.tableRepository.findOne({ where: { id: createTableDto.parentId } })
      if (parent) {
        table.parent = parent
      }
    }
    return this.tableRepository.save(table)
  }

  async findAll(query: QueryTableDto) {
    const { title, pageIndex = 1, pageSize = 10 } = query
    const where: any = {}
    if (title) {
      where.title = Like(`%${title}%`)
    }

    const [list, total] = await this.tableRepository.findAndCount({
      where,
      skip: (pageIndex - 1) * pageSize,
      take: pageSize,
      order: { createdAt: 'DESC' }
    })

    return {
      list,
      total
    }
  }

  async findTree(query: QueryTableDto) {
    const { title, pageIndex = 1, pageSize = 10 } = query
    // For tree structure, we might want to return top-level nodes and their children
    // But standard TypeORM tree repository usage is a bit complex with pagination on top level.
    // For simplicity, we will just return a flat list or use findTrees if no pagination is strictly required on root.
    // However, the mock implementation filters by title and then paginates.
    // Let's try to mimic that behavior but with a real tree structure if possible, or just return flat list for now if tree view constructs it.
    // The frontend expects `children` property.

    // If title is present, we search and return matches (breaking tree structure visually usually, or we need to find parents).
    // If no title, we return trees.

    if (title) {
      const where: any = { title: Like(`%${title}%`) }
      const [list, total] = await this.tableRepository.findAndCount({
        where,
        skip: (pageIndex - 1) * pageSize,
        take: pageSize,
        order: { createdAt: 'DESC' }
      })
      return { list, total }
    } else {
      // Note: Pagination on trees is tricky.
      // We will paginate top-level nodes (roots).
      const roots = await this.tableRepository.find({
        where: { parent: null },
        skip: (pageIndex - 1) * pageSize,
        take: pageSize,
        order: { createdAt: 'DESC' }
      })

      // Load children for these roots
      // This is a simplified approach. For deep trees, use TreeRepository methods.
      // But since we defined it as closure-table, we can use findDescendantsTree.
      // But findDescendantsTree works on a single entity.

      // Let's just return roots and let them load children if needed, OR
      // use a simple find with relations if depth is shallow.
      // The mock data has children.

      // Let's try to load full tree for the page roots.
      const list = []
      for (const root of roots) {
        const tree = await this.tableRepository.manager
          .getTreeRepository(TableExample)
          .findDescendantsTree(root)
        list.push(tree)
      }

      const total = await this.tableRepository.count({ where: { parent: null } })

      return { list, total }
    }
  }

  async findOne(id: string) {
    return this.tableRepository.findOne({ where: { id } })
  }

  async update(id: string, updateTableDto: UpdateTableDto) {
    await this.tableRepository.update(id, updateTableDto)
    return this.findOne(id)
  }
  async save(body: any) {
    // Map snake_case to camelCase for specific fields
    if (body.display_time) {
      body.displayTime = body.display_time
    }
    if (body.image_uri) {
      body.imageUri = body.image_uri
    }

    if (body.id) {
      return this.update(body.id, body)
    } else {
      return this.create(body)
    }
  }

  async remove(ids: string[]) {
    return this.tableRepository.delete({ id: In(ids) })
  }

  // Card Example Methods
  async findCardList(query: QueryCardDto) {
    const { name, pageIndex = 1, pageSize = 10 } = query
    const where: any = {}
    if (name) {
      where.name = Like(`%${name}%`)
    }

    const [list, total] = await this.cardRepository.findAndCount({
      where,
      skip: (pageIndex - 1) * pageSize,
      take: pageSize,
      order: { createdAt: 'DESC' }
    })

    return {
      list,
      total
    }
  }
}
