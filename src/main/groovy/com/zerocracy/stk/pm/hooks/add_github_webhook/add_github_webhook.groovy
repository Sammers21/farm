/*
 * Copyright (c) 2016-2018 Zerocracy
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
package com.zerocracy.stk.pm.hooks.add_github_webhook

import com.jcabi.github.Coordinates
import com.jcabi.github.Github
import com.jcabi.github.Hook
import com.jcabi.github.Repo
import com.jcabi.xml.XML
import com.zerocracy.Farm
import com.zerocracy.Project
import com.zerocracy.entry.ExtGithub
import com.zerocracy.farm.Assume
import com.zerocracy.farm.props.Props
import com.zerocracy.pm.ClaimIn
import org.cactoos.list.Mapped
import org.cactoos.map.MapEntry
import org.cactoos.map.MapOf

// @todo #1226:30min Add property github.webhook.url to production
//  environment. We need adding this property to production environment
//  because it points to github hooks page. This value must be set to
//  http://www.rehttp.net/p/https://www.0crat.com/ghook
def exec(Project project, XML xml) {
  new Assume(project, xml).notPmo()
  new Assume(project, xml).type('Project link was added')
  ClaimIn claim = new ClaimIn(xml)
  if (claim.param('rel') == 'github') {
    Props props = new Props();
    Farm farm = binding.variables.farm
    Github github = new ExtGithub(farm).value()
    Repo repo = github.repos().get(new Coordinates.Simple(claim.param('href')))
    repo.hooks().create(
      'web',
      new MapOf<String, String>(
        new MapEntry<String, String>('url',props.get('//github/webhook.url', '/ghook'))
      ),
      true
    )
  }
}
