#!/usr/bin/groovy

/*
reportGitBranch - Create a report file with information about the repositories used for the build.
 arguments:
  - paths: array of source repository paths
  - output: output file path
*/

def call(paths, output) {
    //paths.each {
        withEnv(["paths=${paths.join(' ')}", "output=${output}"]) {
            sh """
                for path in \${paths} ; do
                pushd "\${path}" || exit
                    repository="\$(basename "\${PWD}")"
                    origin="\$(git config --get remote.origin.url)"
                    branch="\$(git for-each-ref --format='%(upstream:short)' "\$(git symbolic-ref -q HEAD)")"

                    echo \${repository}: \${origin} \${branch#*/} >> "\${output}"
                popd
                done
            """
        }
    //}
}
