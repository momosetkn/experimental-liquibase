package momosetkn

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import momosetkn.liquibase.LiquibaseCommand
import momosetkn.liquibase.change.ExampleCustomTaskChange

class MigrateAndSerializeSpec : FunSpec({
    beforeSpec {
        Database.start()
    }
    afterSpec {
        Database.stop()
    }

    context("migrate -> rollback") {
        test("execute and rollback count is Correct") {
            val container = Database.startedContainer
            LiquibaseCommand.command(
                driverClassName = "org.postgresql.Driver",
                jdbcUrl = container.jdbcUrl,
                user = container.username,
                password = container.password,
                command = "update",
                changelogFile = PARSER_INPUT_CHANGELOG,
            )
            ExampleCustomTaskChange.executeCallCount shouldBe 1
            ExampleCustomTaskChange.rollbackCallCount shouldBe 0

            LiquibaseCommand.command(
                driverClassName = "org.postgresql.Driver",
                jdbcUrl = container.jdbcUrl,
                user = container.username,
                password = container.password,
                command = "rollback",
                changelogFile = PARSER_INPUT_CHANGELOG,
                "--tag=started",
            )

            ExampleCustomTaskChange.executeCallCount shouldBe 1
            ExampleCustomTaskChange.rollbackCallCount shouldBe 1 // actual is 2
        }
    }
})

const val PARSER_INPUT_CHANGELOG = "db.changelog/db.changelog-0.xml"
