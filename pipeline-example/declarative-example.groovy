pipeline {
    agent any
    parameters {
        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?'),
        string(name: 'MYID', defaultValue: '1234', description: 'my id'),
        string(name: 'MYAGE', defaultValue: '12', description: 'my age')
    }
    stages {
        stage('Example') {
            steps {
                echo "Hello ${params.PERSON}"
            }
        }
    }
}
