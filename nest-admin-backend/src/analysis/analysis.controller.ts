import { Controller, Get } from '@nestjs/common';
import { AnalysisService } from './analysis.service';
import { ApiTags } from '@nestjs/swagger';

@ApiTags('analysis')
@Controller('analysis')
export class AnalysisController {
  constructor(private readonly analysisService: AnalysisService) { }

  @Get('total')
  getTotal() {
    return this.analysisService.getTotal();
  }

  @Get('userAccessSource')
  getUserAccessSource() {
    return this.analysisService.getUserAccessSource();
  }

  @Get('weeklyUserActivity')
  getWeeklyUserActivity() {
    return this.analysisService.getWeeklyUserActivity();
  }

  @Get('monthlySales')
  getMonthlySales() {
    return this.analysisService.getMonthlySales();
  }
}
