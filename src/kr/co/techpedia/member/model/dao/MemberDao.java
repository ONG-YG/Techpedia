package kr.co.techpedia.member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.co.techpedia.common.JDBCTemplate;
import kr.co.techpedia.member.model.vo.CompanyInfo;
import kr.co.techpedia.member.model.vo.MemberTypeInfo;
import kr.co.techpedia.member.model.vo.TpMember;

public class MemberDao {

	public TpMember selectOneMember(Connection conn, TpMember member) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		TpMember loginMember = null;
		
		String query = "SELECT * FROM TP_MEMBER WHERE MEMBER_ID=? AND MEMBER_PW=?";
		// Active 여부는 Servlet에서 직접 memberActive 값 확인
		// 관리자 페이지에서 Active 여부 상관없이 멤버 정보 가져올 수 있도록 메소드 작성
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				loginMember = new TpMember();
				loginMember.setMemberNo(rset.getInt("MEMBER_NO"));
				loginMember.setMemberId(rset.getString("MEMBER_ID"));
				loginMember.setMemberTypeCD(rset.getString("MEMBER_TYPECD"));
				loginMember.setCompNo(rset.getInt("COMP_NO"));
				loginMember.setMemberActive(rset.getString("MEMBER_ACTIVE").charAt(0));
				loginMember.setMemberPhoto(rset.getString("MEMBER_PHOTO"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return loginMember;
	}

	public int joinMember(Connection conn, TpMember member) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "INSERT INTO TP_MEMBER VALUES (SEQ_MEMNO.NEXTVAL," +
														"?," + //회원번호
														"?," + //회원아이디
														"?," + //회원비밀번호
														"?," + //회원개인연락처
														"?," + //회원회사연락처
														"?," + //회원이메일
														"?," + //회원구분코드
														"?," + //소속기업 번호
														"?," + //소속기업사번
														"'N'," + //회원활성화여부
														"NULL," + //회원탈퇴번호
														"NULL," + //프로필사진
														"SYSDATE," + //가입일
														"NULL" + //가입승인일
														")";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getMemberPw());
			pstmt.setString(3, member.getMemberName());
			if(member.getMemberPrivatePhone()=="") {
				pstmt.setNull(4, java.sql.Types.VARCHAR);
			}
			else {
				pstmt.setString(4, member.getMemberPrivatePhone());
			}
			pstmt.setString(5, member.getMemberCompanyPhone());
			pstmt.setString(6, member.getMemberEmail());
			pstmt.setString(7, member.getMemberTypeCD());
			pstmt.setInt(8, member.getCompNo());
			pstmt.setString(9, member.getCompanyMemNo());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}
	
	public ArrayList<MemberTypeInfo> getMemberTypeList(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<MemberTypeInfo> memberTypeList = new ArrayList<>();
		
		String query = "SELECT * FROM MEM_TYPE";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				MemberTypeInfo memberType = new MemberTypeInfo();
				memberType.setMemberTypeCD( rset.getString("MEMBER_TYPECD") );
				memberType.setMemberTypeName( rset.getString("MEMBER_TYPENAME") );
				memberType.setCompCTG( rset.getString("COMP_CTG"));
				memberTypeList.add(memberType);
				//System.out.println("dao memberType check\n"+memberType);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return memberTypeList;
	}
	
	public ArrayList<CompanyInfo> getCompanyList(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<CompanyInfo> companyList = new ArrayList<>();
		
		String query = "SELECT * FROM COMPANY_L";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				CompanyInfo company = new CompanyInfo();
				company.setCompNo(rset.getInt("COMP_NO"));
				company.setCompName(rset.getString("COMP_NAME"));
				company.setCompCTG(rset.getString("COMP_CTG"));
				companyList.add(company);
				//System.out.println("dao company check\n"+company);//////////////
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return companyList;
	}

	public boolean checkIdUnique(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		boolean result = true; //조회하는 아이디가 이미 존재하면 true
		
		String query = "SELECT * FROM TP_MEMBER WHERE MEMBER_ID=? AND MEMBER_DELNO IS NULL";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				//조회하는 아이디가 이미 존재
				result = true;
				//System.out.println("DAO : "+rset.getString("MEMBER_ID"));///////////////////////
			}
			else {
				//조회하는 아이디가 존재하지 않음
				result = false;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}

}
