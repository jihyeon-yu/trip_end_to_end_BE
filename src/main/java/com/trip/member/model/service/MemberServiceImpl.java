package com.trip.member.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.trip.member.model.dto.MemberFileInfoDto;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.trip.member.model.dto.MemberChangePasswordDto;
import com.trip.member.model.dto.MemberDto;
import com.trip.member.model.dto.MemberLoginRequestDto;
import com.trip.member.model.mapper.MemberMapper;
import com.trip.security.JwtUtil;
import com.trip.security.TokenDto;

import software.amazon.awssdk.services.s3.model.ObjectCannedACL;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AmazonS3 s3Client;

    @Value("${aws.s3.bucketName}")
    private String bucketName;
    
    @Value("${upload.dir}") // application.properties에 저장된 파일 업로드 디렉토리 경로
	private String uploadDir;

    @Override
    public TokenDto login(MemberLoginRequestDto memberLoginRequestDto) {
        MemberDto memberDto = memberMapper.findById(memberLoginRequestDto.getId());
        if (memberDto != null && passwordEncoder.matches(memberLoginRequestDto.getPassword(), memberDto.getPassword())) {
            String accessToken = jwtUtil.generateAccessToken(memberDto.getId());
            String refreshToken = jwtUtil.generateRefreshToken(memberDto.getId());
            // refreshToken을 데이터베이스에 저장
            memberMapper.updateToken(memberDto.getId(), refreshToken);
            return new TokenDto(accessToken, refreshToken);
        }
        return null;
    }

    @Override
    public TokenDto refreshAccessToken(String refreshToken) {
        if (jwtUtil.isTokenExpired(refreshToken)) {
            return null; // 리프레시 토큰이 만료된 경우
        }
        String username = jwtUtil.getUsernameFromToken(refreshToken);
        String newAccessToken = jwtUtil.generateAccessToken(username);
        return new TokenDto(newAccessToken, refreshToken);
    }

    @Override
    public boolean signup(MemberDto memberDto, MultipartFile file) {
        if (memberMapper.findById(memberDto.getId()) != null) {
            return false; // 아이디가 이미 존재하는 경우
        }
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        try {
			memberDto.setImage(uploadFile(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
        memberMapper.insertMember(memberDto);
        return true;
    }
    
    @Override
    public boolean signup(MemberDto memberDto) {
    	if (memberMapper.findById(memberDto.getId()) != null) {
            return false; // 아이디가 이미 존재하는 경우
        }
        memberDto.setPassword(passwordEncoder.encode(memberDto.getPassword()));
        memberMapper.insertMember(memberDto);
    	return true;
    }

    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        s3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);
        return s3Client.getUrl(bucketName, fileName).toString();
    }

    
    @Override
    public boolean checkIdDuplication(String id) {
        return memberMapper.findById(id) != null;
    }

    @Override
    public List<MemberDto> listMember() {
        return memberMapper.listMember();
    }

    @Override
    public boolean updateMember(MemberDto memberDto) {
        if (memberMapper.findById(memberDto.getId()) == null) {
            return false; // 해당 아이디가 존재하지 않는 경우
        }
        
        memberMapper.updateMember(memberDto);
        return true;
    }
    @Override
	public boolean updateMember(MemberDto memberDto, MultipartFile file) {
    	if (memberMapper.findById(memberDto.getId()) == null) {
            return false; // 해당 아이디가 존재하지 않는 경우
        }
    	try {
			memberDto.setImage(uploadFile(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
        memberMapper.updateMember(memberDto);
        return true;
	}
    @Override
    public boolean deleteMember(String id) {
    	MemberDto memberDto = memberMapper.findById(id);
        if (memberDto == null) {
            return false; // 해당 아이디가 존재하지 않는 경우
        }
        memberMapper.deleteMember(id);
        return true;
    }

	@Override
	public MemberDto findById(String memberId) {
		return memberMapper.findById(memberId);
	}

	@Override
	public boolean changePassword(MemberChangePasswordDto memberChangePasswordDto) {
		MemberDto member = memberMapper.findById(memberChangePasswordDto.getId());
		if(member == null) {
			return false;
		}
		
		// 현재 비밀번호가 일치하는지 확인
		if(!passwordEncoder.matches(memberChangePasswordDto.getCurrentPassword(), member.getPassword())) {
			return false;
		}
		
		member.setPassword(passwordEncoder.encode(memberChangePasswordDto.getNewPassword()));
		memberMapper.changePassword(member);
		return true;
	}

	@Override
	public MemberFileInfoDto fileInfo(String memberId) {
		return memberMapper.fileInfo(memberId);
	}

	@Override
	public MemberDto getMemberDtoByMemberId(String memberId) {
		return memberMapper.getMemberDtoByMemberId(memberId);
	}

	
}
