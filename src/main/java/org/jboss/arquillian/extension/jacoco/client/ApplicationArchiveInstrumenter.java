/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.extension.jacoco.client;

import java.util.Map;
import java.util.Map.Entry;

import org.jboss.arquillian.container.test.spi.client.deployment.ApplicationArchiveProcessor;
import org.jboss.arquillian.test.spi.TestClass;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePath;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.Node;
import org.jboss.shrinkwrap.api.asset.Asset;

/**
 * Instrument all Classes found in the User defined @Deployment.
 *
 * @author <a href="mailto:aslak@redhat.com">Aslak Knutsen</a>
 * @version $Revision: $
 */
public class ApplicationArchiveInstrumenter implements ApplicationArchiveProcessor
{
   public void process(Archive<?> applicationArchive, TestClass testClass)
   {
      Map<ArchivePath, Node> classes = applicationArchive.getContent(Filters.include(".*\\.class"));
      for (Entry<ArchivePath, Node> entry : classes.entrySet())
      {
         Asset original = entry.getValue().getAsset();
         applicationArchive.delete(entry.getKey());
         applicationArchive.add(
               new InstrumenterAsset(original), 
               entry.getKey());
      }
   }
}
