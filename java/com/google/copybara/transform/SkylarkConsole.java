/*
 * Copyright (C) 2018 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.copybara.transform;

import com.google.copybara.util.console.AnsiColor;
import com.google.copybara.util.console.Console;
import com.google.devtools.build.lib.skylarkinterface.SkylarkCallable;
import com.google.devtools.build.lib.skylarkinterface.SkylarkModule;
import com.google.devtools.build.lib.skylarkinterface.SkylarkModuleCategory;

@SkylarkModule(name = "Console",
    category = SkylarkModuleCategory.BUILTIN,
    doc = "A console that can be used in skylark transformations to print info, warning or"
        + " error messages.")
public class SkylarkConsole implements Console {

  private int errorCount = 0;
  private final Console delegate;

  public SkylarkConsole(Console delegate) {
    this.delegate = delegate;
  }

  @Override
  public void startupMessage(String version) {
    throw new UnsupportedOperationException("Shouldn't be called from skylark");
  }

  @SkylarkCallable(name = "error",
      doc = "Show an error in the log. Note that this will stop Copybara execution.")
  @Override
  public void error(String message) {
    delegate.error(message);
    errorCount++;
  }

  @Override
  public boolean isVerbose() {
    return delegate.isVerbose();
  }

  @SkylarkCallable(name = "warn", doc = "Show a warning in the console")
  @Override
  public void warn(String message) {
    delegate.warn(message);
  }

  @SkylarkCallable(name = "verbose",
      doc = "Show an info message in the console if verbose logging is enabled.")
  @Override
  public void verbose(String message) {
    delegate.verbose(message);
  }

  @SkylarkCallable(name = "info", doc = "Show an info message in the console")
  @Override
  public void info(String message) {
    delegate.info(message);
  }

  @SkylarkCallable(name = "progress", doc = "Show a progress message in the console")
  @Override
  public void progress(String progress) {
    delegate.progress(progress);
  }

  @Override
  public boolean promptConfirmation(String message) {
    throw new UnsupportedOperationException("Shouldn't be called from skylark");
  }

  @Override
  public String colorize(AnsiColor ansiColor, String message) {
    throw new UnsupportedOperationException("Shouldn't be called from skylark");
  }

  public int getErrorCount() {
    return errorCount;
  }
}
