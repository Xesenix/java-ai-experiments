package experiments.artemis.damage;

import experiments.artemis.damage.Damage.ElementType;

public class Damage
{
	private double power;
	
	
	private ElementType elementType;
	
	
	private AttackType attackType;
	
	
	public Damage(double power)
	{
		setPower(power);
		setElementType(ElementType.PHYSIQUE);
	}


	public Damage(float power, ElementType element)
	{
		setPower(power);
		setElementType(element);
	}


	public void setPower(double power)
	{
		this.power = power;
	}


	public double getPower()
	{
		return power;
	}
	
	public ElementType getElementType()
	{
		return elementType;
	}


	public void setElementType(ElementType elementType)
	{
		this.elementType = elementType;
	}

	public AttackType getAttackType()
	{
		return attackType;
	}


	public void setAttackType(AttackType attackType)
	{
		this.attackType = attackType;
	}

	public static enum AttackType {
		SLASH, // bleeding?
		CRUSH, // stun?
		PRICING, // ...?
		ENERGY // elemental effect
	}
	
	public static enum ElementType {
		PHYSIQUE,
		POISON,
		ACID,
		EARTH,
		WATER,
		FIRE,
		COLD,
		AIR,
		HOLY,
		UNHOLY,
		DARK
	}
}
