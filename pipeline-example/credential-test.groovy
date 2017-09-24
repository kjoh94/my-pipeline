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
        withCredentials([usernamePassword(credentialsId: 'amazon', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            // available as an env variable, but will be masked if you try to print it out any which way
            sh 'echo $PASSWORD'
            // also available as a Groovy variable—note double quotes for string interpolation
            echo "$USERNAME"
            env.AMAZON_USER = USERNAME
        }
        echo "USERNAME=${env.AMAZON_USER}"
    }

}
