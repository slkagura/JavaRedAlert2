package redAlert.utilBean;

/**
 * 表示ShapeUnitFrame中具有阵营颜色的像素坐标
 * 含有颜色信息的像素点坐标
 */
public class ColorPoint {

	private int x;
	private int y;
	
	public ColorPoint() {
		
	}
	public ColorPoint(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
}
