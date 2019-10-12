package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a two wheeled robot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 *
 * Based on BasicOpMode_Linear
 */

@TeleOp(name="Fighting Gnomes Test4Wheels OpMode", group="Linear Opmode")
//@Disabled
public class FightingGnomes_Test4Wheels_OpMode extends LinearOpMode {

    // Declare OpMode members.
    private HardwareBot robot   = new HardwareBot();
    private ElapsedTime runtime = new ElapsedTime();
    private ElapsedTime period = new ElapsedTime();


    public void local_initialization() {

    }

    @Override
    public void runOpMode() {
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize all members, both robot and local, if needed
        this.robot.init(this.hardwareMap);
        this.local_initialization();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();
        runtime.reset();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            // This is just a simple test op mode that will allow all four wheels to be independently controlled and tested
            // And only them!
            //  public void testDrive(double lfd, double lrd, double rfd, double rrd) {
            this.robot.testDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x, gamepad1.right_stick_y);

            /*
            // The first block of code is for the Driver controller
            // The driver uses gamepad1
            // If you want to use the simple drive mode, then uncomment this next block of code
            //this.robot.simpleDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            // Otherwise use the MecanumDrive
            // This code uses the MecanumDrive method for controlling the robot
            //this.robot.mecanumDrive(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);

            // The second block of code is for the Operator controller
            // The operator uses gamepad2
            // Left_Bumper brings the arm in and Right Bumper sends it out
            // Left Stick is for the lift_drive (up brings it up) and (down brings it down).

            // Lift
            // 20191005 This is the slide drive for lifting the arm up and down
            double lift_amount;
            lift_amount = this.gamepad2.left_stick_y;

            // Arm Rotation
            // 20191005 This is the rotation of the arm to control; the servo is attached to the
            //  sliding lift arm
            // this.gamepad2.right_stick_y controls the arm rotation
            double arm_amount;
            arm_amount = this.gamepad2.right_stick_y;

            // BELOW IS ALL OUT OF DATE FROM 2018

            // Arm Motion in and Out
            // First we need to figure out if the operator wants the arm to go in or out or nothing
            int arm_motion = 0;
            if (this.gamepad2.right_bumper) {
                arm_motion = 1;
            }
            else if (this.gamepad2.left_bumper) {
                arm_motion = -1;
            }

            // Claw Motion in and Out
            // First we need to figure out if the operator wants the claw to go in or out or nothing
            int claw_motion = 0;
            if (this.gamepad2.a) {
                claw_motion = 1;
            }
            else if (this.gamepad2.b) {
                claw_motion = -1;
            }
            // Actually send the operator to the control.
            this.robot.mechanismControl(lift_amount, arm_amount, arm_motion, claw_motion);

            */
            // Show the elapsed game time and wheel power.
            //telemetry.addData("Status", "Run Time: " + runtime.toString());
            //telemetry.addData("Motors", "left (%.2f), right (%.2f)", drivePower, turnPower);
            telemetry.addData("DEBUG", "lfd (%.2f), lrd (%.2f)", gamepad1.left_stick_x, gamepad1.left_stick_y);
            telemetry.update();
        }
    }
}

