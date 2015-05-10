/**
 *  Copyright (c) 1997-2013, www.tinygroup.org (luo_guo@icloud.com).
 *
 *  Licensed under the GPL, Version 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.gnu.org/licenses/gpl.html
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.tinygroup.xmlparser;

import org.tinygroup.xmlparser.node.XmlNode;

import junit.framework.TestCase;

public class INoteTest2 extends TestCase {
	XmlNode node = null;

	protected void setUp() throws Exception {
		node = new XmlNode(XmlNodeType.COMMENT);
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetFooter() {
		StringBuffer sb = new StringBuffer();
		node.getFooter(sb);
		assertEquals("-->", sb.toString());
	}
}
