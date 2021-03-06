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
package org.tinygroup.net.test;

import java.io.ByteArrayInputStream;
import java.io.ObjectOutputStream;

import org.jboss.netty.handler.codec.serialization.ClassResolvers;
import org.objenesis.strategy.StdInstantiatorStrategy;
import org.tinygroup.commons.io.ByteArrayOutputStream;

import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class TestTime {
	public static void main(String[] args) throws Exception {
		int time = 10000;
		long time1 = System.currentTimeMillis();
		for (int i = 0; i < time; i++) {
			HessianOutput hout = new HessianOutput(new ByteArrayOutputStream());
			hout.writeObject(Simple.getSimple());
			hout.flush();
			hout.close();
		}
		long time2 = System.currentTimeMillis();
		System.out.println(time2 - time1 + ":hession write");

		// ===================================================
		ByteArrayOutputStream outStream1 = new ByteArrayOutputStream();
		HessianOutput hout = new HessianOutput(outStream1);
		hout.writeObject(Simple.getSimple());
		hout.flush();
		hout.close();
		// ===================================================
		long time22 = System.currentTimeMillis();
		for (int i = 0; i < time; i++) {
			HessianInput hin = new HessianInput(new ByteArrayInputStream(outStream1.toByteArray().toByteArray()));
			hin.readObject();
			hin.close();
		}
		long time3 = System.currentTimeMillis();
		System.out.println(time3 - time22 + ":hession read");
		// ===================================================
		
		
		
		long time32 = System.currentTimeMillis();
		for (int i = 0; i < time; i++) {
			ObjectOutputStream oout = new CompactObjectOutputStream(
					new ByteArrayOutputStream());
			oout.writeObject(Simple.getSimple());
			oout.flush();
		}
		long time4 = System.currentTimeMillis();
		System.out.println(time4 - time32 + ":Object write");
		// ===================================================
		ByteArrayOutputStream outStream2 = new ByteArrayOutputStream();
		ObjectOutputStream oout = new CompactObjectOutputStream(
				outStream2);
		oout.writeObject(Simple.getSimple());
		oout.flush();
		
		// ===================================================
		long time42 = System.currentTimeMillis();
		for (int i = 0; i < time; i++) {
			CompactObjectInputStream in = new CompactObjectInputStream(
					new ByteArrayInputStream(outStream2.toByteArray().toByteArray()),
					ClassResolvers.cacheDisabled(null));
			in.readObject();
			in.close();
		}
		long time5 = System.currentTimeMillis();
		System.out.println(time5 - time42 + ":Object read");
		// ===================================================
		Kryo kryo = new Kryo();
		kryo.setReferences(false);
		kryo.setRegistrationRequired(false);
		kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());

		// ===================================================
		long time6 = System.currentTimeMillis();
		for (int i = 0; i < time; i++) {
			Output output = new Output(1024);
			output.setOutputStream(new ByteArrayOutputStream());
			kryo.writeClassAndObject(output, Simple.getSimple());
			output.flush();
			output.close();
		}
		long time7 = System.currentTimeMillis();
		System.out.println(time7 - time6 + ":kryo write");
		
		// ===================================================
		
		ByteArrayOutputStream outStream3 = new ByteArrayOutputStream();
		Output output = new Output(1024000);
		output.setOutputStream(outStream3);
		kryo.writeClassAndObject(output, Simple.getSimple());
		output.flush();
		output.close();
		
		// ===================================================
		
		long time72 = System.currentTimeMillis();
		Input in = new Input(1024);
		for (int i = 0; i < time; i++) {
			in.setInputStream(new ByteArrayInputStream(outStream3.toByteArray().toByteArray()));
			kryo.readClassAndObject(in);
			in.close();
		}
		long time8 = System.currentTimeMillis();
		System.out.println(time8 - time72 + ":kryo read");
	}
}
