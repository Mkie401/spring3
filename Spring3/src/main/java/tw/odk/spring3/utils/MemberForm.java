package tw.odk.spring3.utils;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {
	private String account;
	private List<MultipartFile> files;
	
	
	
}
