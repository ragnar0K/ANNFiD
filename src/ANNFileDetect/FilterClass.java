/*
 * Copyright 2013 ragnar0k@fabytes.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ANNFileDetect;

/**
 *
 * @author ragnar0k@fabytes.com
 */
import java.io.File;
import javax.swing.filechooser.FileFilter;

class FilterClass extends FileFilter {
    String[] ext;
    String description;
    public FilterClass(String description, String ext) { 
        this(description, new String[] { ext });
    } 
    
    public FilterClass(String description, String[] ext) { 
        this.ext = (String[]) ext.clone();
        this.description = description;
    } 
    
    @Override
    public String getDescription() {
        return description;
    }
    
    public boolean accept(File file) {
        if (file.isDirectory()) 
                return true;
        else {
            String path = file.getAbsolutePath().toLowerCase();
              for (int i = 0, n = ext.length; i < n; i++) {
                     String extension = ext[i];
                     if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.'))
                          return true; 
              }
        }
        return false;
    }
}

