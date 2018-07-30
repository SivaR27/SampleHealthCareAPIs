import java.util.List;

/**
 * 
 */

/**
 * @author NeeruPK
 *
 */
public interface MemberDAO {	
	

	public Member getMemberById(int memId);
	
	public Member getMemberByName(String memName);
			
	public List<Member> getAllMembers();	
	
	public int deleteMember(int memId);
	
	// Creates a new record, Errors out If tried multiple times, when duplicates not allowed
	public int postMember(Member member);
	
	//Updates an existing membership record if record present, else inserts the record
	public int putMember(Member member);
}

