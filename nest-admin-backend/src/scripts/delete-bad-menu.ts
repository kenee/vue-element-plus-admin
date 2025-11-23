import { NestFactory } from '@nestjs/core'
import { AppModule } from '../app.module'
import { getRepositoryToken } from '@nestjs/typeorm'
import { Menu } from '../menu/entities/menu.entity'
import { Repository } from 'typeorm'

async function bootstrap() {
  const app = await NestFactory.createApplicationContext(AppModule)
  const menuRepository = app.get<Repository<Menu>>(getRepositoryToken(Menu))

  console.log('Starting bad menu deletion...')

  const badPath = 'https://element-plus-admin-doc.cn/'
  const menus = await menuRepository.findBy({ path: badPath })

  if (menus.length === 0) {
    console.log(`No menus found with path: ${badPath}`)
  } else {
    console.log(`Found ${menus.length} bad menu(s). Deleting...`)
    await menuRepository.remove(menus)
    console.log('Deletion complete.')
  }

  await app.close()
}

bootstrap()
