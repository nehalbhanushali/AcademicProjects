package BusinessLogic;

//import Business.Network.Network;
import BusinessLogic.Network.Network;
import BusinessLogic.Organization.Organization;
import BusinessLogic.Role.Role;
import BusinessLogic.Role.SystemAdminRole;
import java.util.ArrayList;

/**
 *
 * @author nehal
 */
public class EcoSystem extends Organization {
    
    
        private static EcoSystem business;
    private ArrayList<Network> networkList;
    
  

    public static EcoSystem getInstance() {
        if (business == null) {
            business = new EcoSystem();
        }
        return business;
    }

    private EcoSystem() {
        super(null);
        networkList = new ArrayList<>();
    }

    public ArrayList<Network> getNetworkList() {
        return networkList;
    }

    public Network createAndAddNetwork() {
        Network network = new Network();
        networkList.add(network);
        return network;
    }

    @Override
    public ArrayList<Role> getSupportedRole() {
        ArrayList<Role> roleList = new ArrayList<>();
        roleList.add(new SystemAdminRole());
        return roleList;
    }

    public boolean checkIfUsernameIsUnique(String username) {

        if (!this.getUserAccountDirectory().checkIfUsernameIsUnique(username)) {
            return false;
        }

        for (Network network : networkList) {
        }

        return true;
    }
}
    
    
    


//    public boolean checkIfUsernameIsUnique(String username) {
//
//        if (!this.getUserAccountDirectory().checkIfUsernameIsUnique(username)) {
//            return false;
//        }
//
//        for (Network network : networkList) {
//        }
//
//        return true;
//    }
