package userData;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DeepCopier {

	/*
	 * 사용시 주의할 점
	 * 
	 * 직렬화를 이용하는 방법이므로 직렬화를 지원하지 않는 클래스를 복사할려면
	 * 직렬화 가능한 클래스로 변환하여야한다. ex) BufferedImage --> ImageIcon
	 * 따라서 직렬화 후 transient로 선언된 변수를 다시 원래대로 복원하주어야 한다.
	 */
	public static Object deepCopy(Object original) throws Exception {
		ObjectOutputStream output = null;
		ObjectInputStream input = null;
		try {
			
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			
			output = new ObjectOutputStream(byteOutput);
			output.writeObject(original);
			output.flush();
			
			ByteArrayInputStream byteInput = new ByteArrayInputStream(byteOutput.toByteArray());
			input = new ObjectInputStream(byteInput);
			
			return input.readObject();
			
		} catch (Exception e) {
			throw e;
		} finally {
			if(output != null)	output.close();
			if(input != null)	input.close();
		}
	}

}
