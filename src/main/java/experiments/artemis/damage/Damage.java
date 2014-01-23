package experiments.artemis.damage;

public class Damage
{
	private double power;
	
	
	public Damage(double power)
	{
		setPower(power);
	}


	public void setPower(double power)
	{
		this.power = power;
	}


	public double getPower()
	{
		return power;
	}
}
