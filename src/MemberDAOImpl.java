import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 */

/**
 * @author NeeruPK
 *
 */

public class MemberDAOImpl implements MemberDAO {
	
 	private static final String DELETE = "DELETE FROM member WHERE memId=?";
 	private static final String FIND_ALL = "SELECT * FROM member ORDER BY memId"; 
 	private static final String FIND_BY_ID = "SELECT * FROM member WHERE memId=?"; 
 	private static final String FIND_BY_NAME = "SELECT * FROM member WHERE memName=? LIMIT 1";
 	//TODO Created by should the user executing the record or some thing like Web Service
 	private static final String INSERT = "INSERT INTO member(memId, memName, address1, address2, gender, birthDate, status, "
 			+ "createdBy, createdTime, lastModifiedBy, lastModifiedTime) "
 			+ "VALUES(?, ?, ?, ?, ?, ?, ?, 'RestServ', date(sysdate()), 'RestServ', date(sysdate()))";
 	private static final String UPDATE = "UPDATE member SET memName=?, address1=?, address2=?, gender=,"
 			+ " birthDate=?, status=?, lastModifiedBy='RestServ', lastModifiedTime=select date(sysdate()) WHERE memID=?";
 	private static final String FIND_IF_PRESENT = "SELECT memId FROM member WHERE memId=?";

 	Connection conn = null;
	PreparedStatement stmt = null; 
	ResultSet resultSet = null;
 	
	@Override
	public Member getMemberById(int memId) {
		
		try	{
		stmt = conn.prepareStatement(FIND_BY_ID);
		stmt.setInt(1, memId);
		resultSet = stmt.executeQuery();
		
		if (resultSet.next())	{
			Member member = new Member();
			member.setMemId(resultSet.getInt("memID"));
			member.setMemName(resultSet.getString("memName"));
			member.setAddress1(resultSet.getString("address1"));
			member.setAddress2(resultSet.getString("address2"));
			member.setGender(resultSet.getString("gender"));
			member.setBirthDate(resultSet.getDate("birthDate"));
			member.setStatus(resultSet.getString("status"));
			member.setCreatedBy(resultSet.getString("createdBy"));
			member.setCreatedTime(resultSet.getDate("createdTime"));
			member.setLastModifiedBy(resultSet.getString("lastModifiedBy"));
			member.setLastModifiedTime(resultSet.getDate("lastModifiedTime"));
			
			return member;			
			}
		else return null;
		
		}	catch (SQLException e) {
    	throw new RuntimeException(e); 
			}		
	}	

	@Override
	public Member getMemberByName(String memName) {
		
		try	{
		stmt = conn.prepareStatement(FIND_BY_NAME);		
		stmt.setString(1, memName);
		stmt.executeQuery();
		
		if (resultSet.next())	{
			Member member = new Member();
			member.setMemId(resultSet.getInt("memID"));
			member.setMemName(resultSet.getString("memName"));
			member.setAddress1(resultSet.getString("address1"));
			member.setAddress2(resultSet.getString("address2"));
			member.setGender(resultSet.getString("gender"));
			member.setBirthDate(resultSet.getDate("birthDate"));
			member.setStatus(resultSet.getString("status"));
			member.setCreatedBy(resultSet.getString("createdBy"));
			member.setCreatedTime(resultSet.getDate("createdTime"));
			member.setLastModifiedBy(resultSet.getString("lastModifiedBy"));
			member.setLastModifiedTime(resultSet.getDate("lastModifiedTime"));
			
			return member;			
			} 
		else return null;
		} catch (SQLException e) {
			    	throw new RuntimeException(e); 
				}	
	}	
	

	@Override
	public List<Member> getAllMembers() {
		// TODO Auto-generated method stub
		return null;
	}
	

	@Override
	public int deleteMember(int memId) {
		
		try {
			stmt = conn.prepareStatement(DELETE);
			stmt.setInt(1, memId);
			return stmt.executeUpdate();
			
		} catch (SQLException e) {		
			throw new RuntimeException(e); 
			}	
	}

	@Override
	public int putMember(Member member) {
		try {
			stmt = conn.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, member.getMemId());
			stmt.setString(2, member.getMemName());
			stmt.setString(3,member.getAddress1());
			stmt.setString(4, member.getAddress2());
			stmt.setString(5, member.getGender());
			stmt.setDate(6, (Date) member.getBirthDate());
			stmt.setString(7, member.getStatus());
			
			int result = stmt.executeUpdate();
			ResultSet resultSet = stmt.getGeneratedKeys();
			
			if (resultSet.next())	{
				member.setMemId(resultSet.getInt(1));
			} 
			return result;			
			
		} catch (SQLException e) {		
			e.printStackTrace();
			throw new RuntimeException(e); 
			}
	}

	@Override
	public int putMember(Member member) {
		
		try {
			stmt = conn.prepareStatement(FIND_IF_PRESENT);
			stmt.setInt(1, member.getMemId());
			ResultSet resultSet1 = stmt.executeQuery();
			
			// Insert a new record, If the record not present in member table
			if (!resultSet1.next())	{
				return PostMember(member);
				}
			// Update the record, If record present in table
			else	{
				//stmt = conn.prepareStatement(UPDATE, Statement.RETURN_GENERATED_KEYS);
				stmt = conn.prepareStatement(UPDATE);
				stmt.setString(1, member.getMemName());
				stmt.setString(2,member.getAddress1());
				stmt.setString(3, member.getAddress2());
				stmt.setString(4, member.getGender());
				stmt.setDate(5, (Date) member.getBirthDate());
				stmt.setString(6, member.getStatus());
				
				return stmt.executeUpdate();	
			
				} 
		}catch(SQLException e) {		
			throw new RuntimeException(e); 
				}	
	}
	}

