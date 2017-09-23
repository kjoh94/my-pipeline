properties([
    parameters([
        string(name: 'DEPLOY_ENV', defaultValue: 'TESTING', description: 'The target environment' ),
        choice(name: 'SOME_CHOICE', choices: ["A", "B", "C"].join("\n"), description: 'Some choice parameter'),
    ])
])

node('slave1') {
    echo 'Hello World'
    echo "workspace=${env.WORKSPACE}"

    env.WORKBASE = env.WORKSPACE.replace(env.JOB_NAME, '')
    echo "workbase=${env.WORKBASE}"

    dir (env.WORKSPACE)
    {
        fileOperations([fileCreateOperation(fileContent: '1234', fileName: 'a.txt')])
        sleep time: 10, unit: 'MINUTES'
        echo "I'm sleeping..."
    }
}
