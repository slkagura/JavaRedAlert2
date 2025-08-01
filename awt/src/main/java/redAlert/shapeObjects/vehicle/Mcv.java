package redAlert.shapeObjects.vehicle;

import java.util.ArrayList;
import java.util.List;

import redAlert.Constructor;
import redAlert.GameContext;
import redAlert.GlobalConfig;
import redAlert.enums.UnitColor;
import redAlert.militaryBuildings.AfCnst;
import redAlert.resourceCenter.ShapeUnitResourceCenter;
import redAlert.shapeObjects.Expandable;
import redAlert.shapeObjects.Vehicle;
import redAlert.utilBean.CenterPoint;
import redAlert.utils.PointUtil;

/**
 * 盟军基地车
 */
public class Mcv extends Vehicle implements Expandable{

	//------------基地车独有的变量
	public final static int MCV_STATUS_NORMAL = 0;
	public final static int MCV_STATUS_EXPANDING = 1;
	
	public int status = MCV_STATUS_NORMAL;//正常状态
	//---------------
	/**
	 *
	 */
	public Mcv(int positionX,int positionY,UnitColor unitColor) {
		super.initVehicleParam(positionX,positionY, unitColor, "mcv");
		//定义名称
		super.unitName = "盟军基地车";
		//降低基地车的移动速度
		super.frameSpeed = 4;
		//基地车没有攻击能力
		super.attackable = false;
	}
	
	/**
	 * 这个代码真的是写的一坨
	 * 一定要优化  让逻辑更清晰，步骤更合理
	 */
	@Override
	public void calculateNextFrame() {
		
		if(status==MCV_STATUS_NORMAL) {
			
			super.calculateNextFrame();
			
			super.curFrame = bodyFrames.get(curTurn);
			this.positionMinX = positionX+curFrame.getMinX();
			this.positionMinY = positionY+curFrame.getMinY();
		
		}else {
			
			/**
			 * 基地车的展开逻辑
			 */
			setTargetTurn(6);
			
			/**
			 * 车体方向需旋转到位才能移动
			 */
			if(targetTurn!=curTurn) {
				turn();
				super.curFrame = bodyFrames.get(curTurn);
				return;
			}else {
				CenterPoint cp = PointUtil.getCenterPoint(positionX+centerOffX,positionY+centerOffY);
				cp.removeUnit(this);
				ShapeUnitResourceCenter.removeOneMovableUnit(this);
				ShapeUnitResourceCenter.removeOneUnit(this.getBloodBar());
				this.getBloodBar().setVisible(false);
				this.getBloodBar().setEnd(true);
				this.setVisible(false);
				this.setEnd(true);
				AfCnst afCnst = new AfCnst(cp,GlobalConfig.sceneType, GlobalConfig.unitColor);
				Constructor.putOneBuilding(afCnst);//盟军基地
				try {
					Thread.sleep(50);
					Constructor.playOneMusic("ceva049");//New construction options
				}catch (Exception e) {
					e.printStackTrace();
				}
				
				
			}
			
		}
		
		
		
		
		
	}
	
	/**
	 * 展开
	 */
	@Override
	public void expand() {
		//先转到姿势6  然后移走基地车  然后放一个基地
		
		
		this.status = Mcv.MCV_STATUS_EXPANDING;
		
	}
	
	/**
	 * 判定是否满足展开的条件
	 */
	@Override
	public boolean isExpandable() {
		
		List<CenterPoint> result = new ArrayList<>();
		result.add(curCenterPoint.getLeft());
		result.add(curCenterPoint.getLeftDn());
		result.add(curCenterPoint.getDn());
		result.add(curCenterPoint.getRightDn());
		result.add(curCenterPoint.getRight());
		result.add(curCenterPoint.getRightUp());
		result.add(curCenterPoint.getUp());
		result.add(curCenterPoint.getLeftUp());
		
		result.add(curCenterPoint.getLeft().getLeftDn());
		result.add(curCenterPoint.getLeftDn().getLeftDn());
		result.add(curCenterPoint.getLeftDn().getDn());
		result.add(curCenterPoint.getRight().getRightDn());
		result.add(curCenterPoint.getRightDn().getRightDn());
		result.add(curCenterPoint.getDn().getRightDn());
		result.add(curCenterPoint.getDn().getDn());
		
		
		boolean isCanMake = true;
		for(CenterPoint cp:result) {
			if(!cp.isBuildingCanPutOn()) {
				isCanMake = false;
			}
		}
		return isCanMake;
	}
	
	
	/**
	 * 再缩回
	 */
	@Override
	public void unexpand() {
	
	
	}

}
