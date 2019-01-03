/*
 * Copyright (c) 2016-2019 Zerocracy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.zerocracy.bundles.awards_points_to_arc_for_pull_request

import com.jcabi.github.Pull
import com.jcabi.github.Repo
import com.jcabi.github.Repos
import com.jcabi.github.mock.MkGithub
import com.jcabi.xml.XML
import com.zerocracy.Farm
import com.zerocracy.Project
import com.zerocracy.entry.ExtGithub

def exec(Project project, XML xml) {
  Farm farm = binding.variables.farm
  MkGithub github = new ExtGithub(farm).value() as MkGithub
  Repo repo = github.repos().create(new Repos.RepoCreate('test', false))
  Pull pull = repo.pulls().create('Test PR', 'test', 'the test')
  github.relogin('cmiranda')
    .repos().get(repo.coordinates())
    .pulls().get(pull.number())
    .comments().post('PR accepted', 'cmt', '', 1)
  github.relogin('dmarkov')
    .repos().get(repo.coordinates())
    .pulls().get(pull.number())
    .comments().post('rultor merge', 'cmt', '', 1)
}
