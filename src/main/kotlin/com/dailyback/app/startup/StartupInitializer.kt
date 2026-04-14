package com.dailyback.app.startup

import com.dailyback.app.config.SeedConfig
import com.dailyback.shared.application.maintenance.RecurrenceMaintenanceService
import com.dailyback.shared.application.seeds.SeedDefaultCategoriesUseCase
import com.dailyback.shared.application.seeds.SeedScenarioDataUseCase
import com.dailyback.shared.infrastructure.database.DatabaseFactory
import com.dailyback.shared.infrastructure.migration.FlywayRunner

class StartupInitializer(
    private val flywayRunner: FlywayRunner,
    private val databaseFactory: DatabaseFactory,
    private val seedDefaultCategoriesUseCase: SeedDefaultCategoriesUseCase,
    private val seedScenarioDataUseCase: SeedScenarioDataUseCase,
    private val seedConfig: SeedConfig,
    private val recurrenceMaintenanceService: RecurrenceMaintenanceService,
) {
    fun initialize() {
        flywayRunner.migrate()
        databaseFactory.connect()
        if (seedConfig.enabled) {
            seedDefaultCategoriesUseCase.execute()
        }
        if (seedConfig.scenarioEnabled) {
            seedScenarioDataUseCase.execute()
        }
        recurrenceMaintenanceService.execute()
    }
}
