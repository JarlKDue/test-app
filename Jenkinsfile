pipeline {
  agent any
  stages {
    stage('Fetch') {
      steps {
        git(url: 'https://github.com/JarlKDue/test-app.git', branch: 'master', changelog: true)
      }
    }

  }
  environment {
    git_branch = 'master'
    git_repo = 'https://github.com/JarlKDue/test-app.git'
  }
}