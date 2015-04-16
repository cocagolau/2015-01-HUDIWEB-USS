package uss.model;

public class TestResult {
	private String stringId;
	private String result;
	
	public TestResult(String stringId, String result) {
		this.stringId = stringId;
		this.result = result;
	}

	public String getStringId() {
		return stringId;
	}
	
	public String getResult() {
		return result;
	}

	@Override
	public String toString() {
		return "TestResult [stringId=" + stringId + ", result=" + result + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.result == null) ? 0 : this.result.hashCode());
		result = prime * result + ((stringId == null) ? 0 : stringId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TestResult other = (TestResult) obj;
		if (result == null) {
			if (other.result != null)
				return false;
		} else if (!result.equals(other.result))
			return false;
		if (stringId == null) {
			if (other.stringId != null)
				return false;
		} else if (!stringId.equals(other.stringId))
			return false;
		return true;
	}
	
	
	
}
