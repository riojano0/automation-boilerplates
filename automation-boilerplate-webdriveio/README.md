# automation-boilerplate-webdriveio

Boilerplate with wdio 7.17+, cucumber and using page-model-object

## Prerequisites

- Node v14+
- Java 8+ (For Allure)
- Locate on the project and install the modules with ```npm install```

## How run the tests?

Execute

```npm run wdio```


## Why .vscode folder is present?

Because set some quality of life when you are working on vs code how the detection of cucumber with the specific code step and the configuration to allow to debug from the IDE.

## Allure Report is not generate by "The input line is too long"

Currently exist this problem report and exist a PR on https://github.com/allure-framework/allure2/pull/1537
But the workaround is apply that manually on node_modules\allure-commandline\dist\bin\allure.bat the line where is set the CLASSPATH to

```
set CLASSPATH=%APP_HOME%\lib\*;%APP_HOME%\lib\config
```

## Allure report is not generated

If the report fail to generate try to install globally allure ```npm i -g allure-commandline@2.17.2``` and re-try

## Where are store the reports?

All the reports are save on **reports** folder

## How to see the Allure report?

Execute ```npm run allure-report```

## How I can run only one test?

You can use different [cucumber-options](https://cucumber.io/docs/cucumber/api/#options) to achieve that but I recommend to mark the scenario that you want to run with ``@Debug`` and execute ```npm run wdio-debug```