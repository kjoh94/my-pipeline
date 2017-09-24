properties([
    parameters([
        string(name: 'DEPLOY_ENV', defaultValue: 'TESTING', description: 'The target environment' ),
        choice(name: 'SOME_CHOICE', choices: ["A", "B", "C"].join("\n"), description: 'Some choice parameter'),
    ])
])

node('slave1') {
    echo 'Hello World'
    echo "WORKSPACE=${env.WORKSPACE}"
    echo "JOB_NAME=${env.JOB_NAME}"

    env.WORKBASE = env.WORKSPACE.replaceFirst(/${env.JOB_NAME}(@\d+)?\/?$/, '')
    env.WORKSPACE = "${env.WORKBASE}${env.JOB_NAME}-${env.EXECUTOR_NUMBER}"

    echo "WORKSPACE=${env.WORKSPACE}"
    
    dir (env.WORKSPACE)
    {
        fileOperations([fileCreateOperation(fileContent: '1234', fileName: 'a.txt')])
    }

    stage ('credential-test') {
        withCredentials([usernamePassword(credentialsId: '10624a7a-4c9d-4c81-8442-9ff5a2c027de', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            sh 'echo $PASSWORD'
            env.USERID = USERNAME
        }
        echo "USERNAME=${env.USERID}"

        withCredentials([perforcePasswordCredential(credentialsId: '97dd51f7-a592-4145-90fd-efff461f8c05', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            env.USERID = USERNAME
        }
        echo "USERNAME=${env.USERID}"

    }

}
