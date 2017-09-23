node('slave1') {
    echo 'Hello World'
    echo "workspace=${env.WORKSPACE}"

    dir (env.WORKSPACE)
    {
        fileOperations([fileCreateOperation(fileContent: '1234', fileName: 'a.txt')])
        sleep time: 10, unit: 'MINUTES'
        echo "I'm sleeping..."
    }
}
