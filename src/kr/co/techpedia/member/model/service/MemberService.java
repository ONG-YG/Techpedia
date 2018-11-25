package kr.co.techpedia.member.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.co.techpedia.common.JDBCTemplate;
import kr.co.techpedia.member.model.dao.MemberDao;
import kr.co.techpedia.member.model.vo.CompanyInfo;
import kr.co.techpedia.member.model.vo.MemberTypeInfo;
import kr.co.techpedia.member.model.vo.TpMember;

public class MemberService {
	public TpMember selectOneMember(TpMember member) {
		Connection conn = JDBCTemplate.getConnection();
		
		TpMember loginMember = new MemberDao().selectOneMember(conn, member);
		
		JDBCTemplate.close(conn);		
		
		return loginMember;
	}
	
	public TpMember getMemberInfo(String memberId) {
		Connection conn = JDBCTemplate.getConnection();
		
		TpMember memberInfo = new MemberDao().getMemberInfo(conn, memberId);
		
		JDBCTemplate.close(conn);		
		
		return memberInfo;
	}
	
	public int joinMember(TpMember member) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().joinMember(conn, member);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}
		else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);		
		
		return result;
	}
	
	public ArrayList<MemberTypeInfo> getMemberTypeList() {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<MemberTypeInfo> memberTypeList = new ArrayList<>();
		memberTypeList = new MemberDao().getMemberTypeList(conn);
		
		JDBCTemplate.close(conn);
		
		return memberTypeList;
	}
	
	public ArrayList<CompanyInfo> getCompanyList() {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<CompanyInfo> companyList = new ArrayList<>();
		companyList = new MemberDao().getCompanyList(conn);
		
		JDBCTemplate.close(conn);
		
		return companyList;
	}
	
	public boolean checkIdUnique(String memberId) {
		Connection conn = JDBCTemplate.getConnection();
		
		boolean result = new MemberDao().checkIdUnique(conn, memberId);
		
		JDBCTemplate.close(conn);		
		
		return result;
	}

	public int updateMember(TpMember member) {
Connection conn = JDBCTemplate.getConnection();
		
		int result = new MemberDao().updateMember(conn, member);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}
		else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);		
		
		return result;
	}
	
}
