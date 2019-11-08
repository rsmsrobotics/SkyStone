package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import static java.lang.StrictMath.abs;

/**
 * This is NOT an opmode.
 *
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a Pushbot.
 * See PushbotTeleopTank_Iterative and others classes starting with "Pushbot" for usage examples.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left front drive motor:  "left_front_drive"
 * Motor channel:  Right front drive motor:  "right_front_drive"
 * Motor channel:  Left rear drive motor: "left_back_drive"
 * Motor channel:  Right rear drive motor: "right_back_drive"
 * Motor channel:  Block intake motor:  "block_intake_drive"
 * Motor channel:  Arm rotate drive motor:  "arm_rotate_drive"
 * Motor channel:  Arm lift motor: "arm_lift_drive"
 *
 * Servo channel:  Intake left servo: "intake_left_servo"
 * Servo channel:  Intake right servo: "intake_right_servo"
 * Servo channel:  Claw servo: "claw_servo"
 */
public class HardwareBot
{
    /* Public OpMode members. */
    public DcMotor left_front_drive = null;
    public DcMotor left_back_drive = null;
    public DcMotor right_front_drive = null;
    public DcMotor right_back_drive = null;
   // public DcMotor block_intake_drive = null;
    public DcMotor arm_rotate_drive = null;
    public DcMotor arm_lift_drive = null;
    public Servo intake_left_servo = null;
    public Servo intake_right_servo = null;
    public Servo claw_servo = null;

    public static final double FORWARD_SPEED = 0.6;
    public static final double TURN_SPEED    = 0.5;
    public static final double UP_SPEED = 0.5;
    public static final double DOWN_SPEED = 0.5;

    public static final double MECHANUM_DRIVE_SPEED = 0.5;
    public static final double INTAKE_DRIVE_SPEED = 0.33;
    public static final double ARM_ROTATE_SPEED = 0.33;
    public static final double ARM_LIFT_SPEED = 0.33;

    public final static double ARM_ROTATE_HOME = 0.2;
    public final static double ARM_ROTATE_MIN_RANGE = 0.20;
    public final static double ARM_ROTATE_MAX_RANGE = 0.90;

    public final static double ARM_LIFT_HOME = 0.2;
    public final static double ARM_LIFT_MIN_RANGE = 0.20;
    public final static double ARM_LIFT_MAX_RANGE = 0.90;

    public final static double INTAKE_SERVOS_MOTION_DELTA = 0.1;
    /** The servos for the intake will be facing each other and for the same direction of intake,
     * they need to spin in a different direction.
     */
    public static final double INTAKE_INITIAL_SERVO = 0.5;
    public final static double INTAKE_LEFT_SERVO_DIRECTION_MULTIPLIER = 1.0;
    public final static double INTAKE_RIGHT_SERVO_DIRECTION_MULTIPLIER = -1.0;
    public final static double CLAW_INITIAL_SERVO = 0.0;
    public final static double CLAW_MIN_SERVO = -0.5;
    public final static double CLAW_MAX_SERVO = 0.5;
    public final static double CLAW_SERVO_MOTION_DELTA = 0.1;


    /* local OpMode members. */
    HardwareMap hardwareMap     =  null;
    private ElapsedTime period  = new ElapsedTime();

    private boolean arm_being_controlled = false;

    /* Constructor */
    public HardwareBot(){

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        this.hardwareMap = ahwMap;

        // Define and Initialize Motors
        this.left_front_drive = this.hardwareMap.get(DcMotor.class, "left_front_drive");
        this.right_front_drive = this.hardwareMap.get(DcMotor.class, "right_front_drive");
        this.left_back_drive = this.hardwareMap.get(DcMotor.class, "left_back_drive");
        this.right_back_drive = this.hardwareMap.get(DcMotor.class, "right_back_drive");
        this.arm_lift_drive = this.hardwareMap.get(DcMotor.class, "arm_lift_drive");
        this.arm_rotate_drive = this.hardwareMap.get(DcMotor.class, "arm_rotate_drive");
        //this.block_intake_drive = this.hardwareMap.get(DcMotor.class, "block_intake_drive");

        // TODO We do not yet know what the directions should be...
        this.left_front_drive.setDirection(DcMotor.Direction.REVERSE);
        this.right_front_drive.setDirection(DcMotor.Direction.FORWARD);
        this.left_back_drive.setDirection(DcMotor.Direction.REVERSE);
        this.right_back_drive.setDirection(DcMotor.Direction.FORWARD);
        this.arm_lift_drive.setDirection(DcMotor.Direction.FORWARD);
        this.arm_rotate_drive.setDirection(DcMotor.Direction.FORWARD);
        //this.block_intake_drive.setDirection(DcMotor.Direction.FORWARD);

        // Set all motors to zero power
        this.left_front_drive.setPower(0);
        this.right_front_drive.setPower(0);
        this.left_back_drive.setPower(0);
        this.right_back_drive.setPower(0);
        this.arm_lift_drive.setPower(0);
        this.arm_rotate_drive.setPower(0);
        //this.block_intake_drive.setPower(0);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        this.left_front_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.right_front_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.left_back_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.right_back_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        this.arm_rotate_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //this.block_intake_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        this.arm_lift_drive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.arm_lift_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Define and initialize ALL installed servos.
        this.intake_left_servo  = hardwareMap.get(Servo.class, "intake_left_servo");
        this.intake_right_servo  = hardwareMap.get(Servo.class, "intake_right_servo");
        this.intake_left_servo.setPosition(INTAKE_INITIAL_SERVO);
        this.intake_right_servo.setPosition(INTAKE_INITIAL_SERVO);

        this.claw_servo  = hardwareMap.get(Servo.class, "claw_servo");
        this.claw_servo.setPosition(CLAW_INITIAL_SERVO);

        // Unclear if this is used
        this.arm_being_controlled = false;
    }

    public void mecanumDrive(double x, double y, double rotation) {
        double wheelSpeeds[] =new double[4];

        /*  This is the original code for the mechanum drive.
        wheelSpeeds[0] = x + y + rotation;
        wheelSpeeds[1] = -x + y - rotation;
        wheelSpeeds[2] = -x + y + rotation;
        wheelSpeeds[3] = x + y - rotation;
        */

        // tweaking the relative changes so that we can control it better.
        wheelSpeeds[0] = -x + y - rotation;
        wheelSpeeds[1] = x + y + rotation;
        wheelSpeeds[2] = x + y - rotation;
        wheelSpeeds[3] = -x + y + rotation;

        this.normalize_mecanum(wheelSpeeds);

        this.left_front_drive.setPower(wheelSpeeds[0] * this.MECHANUM_DRIVE_SPEED);
        this.right_front_drive.setPower(wheelSpeeds[1] * this.MECHANUM_DRIVE_SPEED);
        this.left_back_drive.setPower(wheelSpeeds[2] * this.MECHANUM_DRIVE_SPEED);
        this.right_back_drive.setPower(wheelSpeeds[3] * this.MECHANUM_DRIVE_SPEED);
    }

    /** Mechanism Control:  intakeControl
     * This is for the intake control in the front of the robot.
     *
     * This relies on the class globals for the INTAKE LEFT and RIGHT.  They need to be opposites
     * (one is negative and one is positive) but they can have different magnitudes, which will
     * directly relate to speed.
     *
     * @param direction -- negative for in and positive for out, based on rear triggers
     */
    public void intakeControl(int direction)
    {
        if (direction != 0 )
        {
            double intakePositionL, intakePositionR;

            intakePositionL = this.intake_left_servo.getPosition();
            intakePositionR = this.intake_right_servo.getPosition();

            this.intake_left_servo.setPosition(intakePositionL +
                    this.INTAKE_LEFT_SERVO_DIRECTION_MULTIPLIER * direction);
            this.intake_right_servo.setPosition(intakePositionR +
                    this.INTAKE_RIGHT_SERVO_DIRECTION_MULTIPLIER * direction);
        }
    }

    /** Mechanism Control:  armControl
     * This is for the arm and the lift control.
     *
     * @todo this function still needs work.
     *
     * @param rotateRequest
     * @param liftRequest
     * @param clawMotion negative to close claw, positive to open claw, zero for nothing
     */
    public void armControl(double rotateRequest, double liftRequest, int clawMotion)
    {

        /* operate the claw itself */
        if (clawMotion != 0) {
            double clawPosition = this.claw_servo.getPosition();
            double newClawPosition;

            newClawPosition = clawPosition +
                    this.CLAW_SERVO_MOTION_DELTA * clawMotion;

            /* This code "clamps" the claw servo position to the allowable space. */
            if (newClawPosition < this.CLAW_MIN_SERVO) {
                newClawPosition = this.CLAW_MIN_SERVO;
            }

            if (newClawPosition > this.CLAW_MAX_SERVO) {
                newClawPosition = this.CLAW_MAX_SERVO;
            }

            this.claw_servo.setPosition(newClawPosition);
        }

        /* operate the rotation */
        /* first we check if the request is anywhere near 0; if so, do nothing. */
        if (abs(rotateRequest) > 0.00000001) {
            // @todo
        }

    }

    /** Commenting this temporarily so that we can have zero errors.  We will need to add this back into it
     * when the robot is working...
     *
    public void mechanismControl(double lift_request, double arm_request, int arm_motion, double claw_motion) {
        // arm_motion is an int that can be value -1 or 0 or 1
        //  0 means not moving the arm in or out
        // -1 means move the arm in
        // +1 means move the arm out
        double arm_position = this.arm_servo.getPosition();
        if (arm_motion == 1)
        {
            this.arm_servo.setPosition(arm_position + this.ARM_SERVO_MOTION_DELTA);
        }
        else if (arm_motion == -1)
        {
            this.arm_servo.setPosition(arm_position - this.ARM_SERVO_MOTION_DELTA);
        }
        else // arm_motion == 0
        {
            this.arm_servo.setPosition(MID_SERVO);
        }
        //this is for the claw
        double claw_position = this.claw_servo.getPosition();
        if (claw_motion == 1)
        {
            this.claw_servo.setPosition(claw_position + this.CLAW_SERVO_MOTION_DELTA);
        }
        else if (claw_motion == -1)
        {
            this.claw_servo.setPosition(claw_position - this.CLAW_SERVO_MOTION_DELTA);
        }
        else // claw_motion == 0
        {
            this.claw_servo.setPosition(MID_SERVO);
        }
        // control the lift_drive
        // lift_request is a floating value between -1.0 and 1.0.
        //  The greater the value, the more the power to the motor.
        this.lift_drive.setPower(lift_request * this.LIFT_DRIVE_MOTOR_SPEED);

        // Arm drive control
        //  if the stick is not being touched, then we use ensure we are driving to position
        //  if the stick is being touched, then we let the operator control it.
        //  if the stick transitions from being touched to not touched, then we record position
        if (Math.abs(arm_request) < this.ARM_CONTROLLER_SENSITIVITY)
        {
            // not controlling the system
            // Check if we switched from controlled to not controlled
            //   if so, set mode to drive by position, and record the position
            if (this.arm_being_controlled)
            {
                int current_position = this.arm_drive.getCurrentPosition();

                this.arm_drive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                this.arm_drive.setTargetPosition(current_position);

                this.arm_being_controlled = false;
            }
        }
        else // operator is controlling the arm
        {
            if (!this.arm_being_controlled) {
                // if we were not being controlled the last time we were here, then
                //  we need to change the motor run mode
                this.arm_drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            }

            this.arm_drive.setPower(arm_request * this.ARM_DRIVE_MOTOR_SPEED);
            this.arm_being_controlled = true;
        }
    }*/

    private void normalize_mecanum(double[] wheelSpeeds) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);

        for (int i = 1; i < wheelSpeeds.length; i++){

            double magnitude = Math.abs(wheelSpeeds[i]);

            if (magnitude > maxMagnitude)
            {
                maxMagnitude= magnitude;
            }
        }
        if (maxMagnitude > 1.0)
        {
            for (int i = 0; i < wheelSpeeds.length; i++)
            {
                wheelSpeeds[i] /= maxMagnitude;
            }
        }
    }

    public void simpleDrive(double x, double y, double rotation) {
        //Setup drive variables for each drive wheel
        double drivePower;
        double turnPower;
        double rotatePower;

        // POV Mode uses left stick to go forward, and right stick to turn.
        // - This uses basic math to combine motions and is easier to drive straight.
        double drive = -y;
        double turn = x;
        double rotate = rotation;
        drivePower = Range.clip(drive + turn, -1.0, 1.0);
        turnPower = Range.clip(drive - turn, -1.0, 1.0);
        rotatePower = Range.clip(rotate, -1.0, 1.0);

        // Tank Mode uses one stick to control each wheel.
        // - This requires no math, but it is hard to drive forward slowly and keep straight.
        // drivePower  = -gamepad1.left_stick_y ;
        // turnPower = -gamepad1.right_stick_y ;

        // Send calculated power to wheels
        this.left_front_drive.setPower(drivePower);
        this.right_front_drive.setPower(turnPower);
    }

    public void testDrive(double lfd, double lrd, double rfd, double rrd) {
        this.left_front_drive.setPower(lfd * this.MECHANUM_DRIVE_SPEED);
        this.right_front_drive.setPower(rfd * this.MECHANUM_DRIVE_SPEED);
        this.left_back_drive.setPower(lrd * this.MECHANUM_DRIVE_SPEED);
        this.right_back_drive.setPower(rrd * this.MECHANUM_DRIVE_SPEED);
    }
}
